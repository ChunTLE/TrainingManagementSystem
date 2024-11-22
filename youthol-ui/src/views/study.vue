<script setup>
import { ref, reactive, computed, watch, nextTick, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import http from '@/util/http'

const calendarData = reactive([])
const selectedDepartment = ref('')
const rowHeights = reactive([])
const loading = ref(true)

const dialogVisible = ref(false)
const dialogMode = ref('edit')
const formData = reactive({
    department: '',
    date: '',
    plan: '',
})

const viewDialogVisible = ref(false);
const viewFormData = reactive({
    date: '',
    department: '',
    plan: '',
    id: ''
});

const filteredCalendarData = computed(() =>
    selectedDepartment.value
        ? calendarData.filter((item) => item.department === selectedDepartment.value)
        : calendarData
)

const onDateClick = (data) => {
    const clickedDate = new Date(data.date).toISOString().split('T')[0]
    formData.date = clickedDate
    formData.department = ''
    formData.plan = ''
    dialogMode.value = 'edit'
    dialogVisible.value = true
}

const onBadgeClick = (data, department) => {
    const clickedDate = new Date(data.date).toISOString().split('T')[0];
    const existingPlan = filteredCalendarData.value.find(item => item.date === clickedDate && item.department === department);

    if (existingPlan) {
        viewDialogVisible.value = true;
        viewFormData.id = existingPlan.id;
        viewFormData.date = existingPlan.date;
        viewFormData.department = existingPlan.department;
        viewFormData.plan = existingPlan.plan;
    }
}

const submitPlan = async () => {
    const newPlan = {
        date: formData.date,
        department: formData.department,
        plan: formData.plan,
    }
    try {
        http.post('/study/add', newPlan).then(() => {
            calendarData.push(newPlan)
            ElMessage.success('计划添加成功')
            dialogVisible.value = false
        }).catch((error) => {
            ElMessage.error('后台忙碌，请稍后再试')
        })
    } catch (error) {
        ElMessage.error('后台忙碌，请稍后再试')
    }
}

const updatePlan = async () => {
    const updatedPlan = {
        date: viewFormData.date,
        department: viewFormData.department,
        plan: viewFormData.plan,
        id: viewFormData.id
    };
    await http.put('/study/update', updatedPlan).then(() => {
        console.log(updatedPlan)
        ElMessage.success('计划更新成功')
        viewDialogVisible.value = false
        getAllData()
    }).catch((error) => {
        ElMessage.error('后台忙碌，请稍后再试');
    });
}

const deletePlan = async () => {
    await ElMessageBox.confirm(
        '确定要删除此计划吗？',
        '提示',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(async () => {
        try {
            await http.post('/study/delete', viewFormData);
            calendarData.splice(calendarData.findIndex(item => item.id === viewFormData.id), 1);
            ElMessage.success('计划删除成功');
            viewDialogVisible.value = false;
        } catch (error) {
            ElMessage.error('删除失败，请稍后再试');
        }
    }).catch(() => {
        ElMessage.info('已取消删除');
    });
}

const getDepartmentsForDate = (date) => {
    const formattedDate = new Date(date).toISOString().split('T')[0]
    const departments = filteredCalendarData.value
        .filter((item) => item.date === formattedDate)
        .map((item) => item.department)
    return [...new Set(departments)]
}

const adjustRowHeights = () => {
    nextTick(() => {
        const rows = document.querySelectorAll('.el-calendar-table__row')
        rowHeights.length = 0
        rows.forEach((row) => {
            const maxBadges = Math.max(
                ...Array.from(row.querySelectorAll('.calendar-day')).map(
                    (day) => day.querySelectorAll('.event-badge').length
                )
            )
            const rowHeight = 50 + maxBadges * 15
            rowHeights.push(rowHeight)
            row.style.height = `${rowHeight}px`
        })
    })
}

watch(
    () => filteredCalendarData.value,
    () => adjustRowHeights(),
    { deep: true, immediate: true }
)

onMounted(() => {
    getAllData()
})

const getAllData = () => {
    http.get('/study/all').then((res) => {
        calendarData.length = 0
        res.data.data.forEach((item) => {
            calendarData.push(item)
        })
        loading.value = false
    })
}
</script>

<template>
    <div class="calendar-wrapper">
        <el-select v-model="selectedDepartment" placeholder="筛选部门" clearable class="mb-4" style="width: 200px;"
            ref="departmentSelect">
            <el-option label="全部" :value="''" />
            <el-option label="综合部" value="综合部" />
            <el-option label="程序部" value="程序部" />
            <el-option label="美工部" value="美工部" />
            <el-option label="媒体中心" value="媒体中心" />
            <el-option label="共建中心" value="共建中心" />
            <el-option label="摄影部" value="摄影部" />
            <el-option label="视频推广部" value="视频推广部" />
            <el-option label="闪客部" value="闪客部" />
        </el-select>

        <el-calendar v-loading="loading" element-loading-text="Loading..." ref="calendar">
            <template #date-cell="{ data }">
                <div class="calendar-day" @click="onDateClick(data)">
                    <div class="date-text">{{ new Date(data.date).toISOString().split('T')[0].slice(5) }}</div>
                    <el-badge v-for="department in getDepartmentsForDate(data.date)" :key="department"
                        class="event-badge" :value="department" type="primary"
                        @click.stop="onBadgeClick(data, department)" />
                </div>
            </template>
        </el-calendar>

        <el-dialog v-model="dialogVisible" title="新增">
            <el-form>
                <el-form-item label="日期">
                    <el-input v-model="formData.date" disabled />
                </el-form-item>
                <el-form-item label="部门">
                    <el-select v-model="formData.department" placeholder="请选择部门">
                        <el-option label="综合部" value="综合部" />
                        <el-option label="程序部" value="程序部" />
                        <el-option label="美工部" value="美工部" />
                        <el-option label="媒体中心" value="媒体中心" />
                        <el-option label="共建中心" value="共建中心" />
                        <el-option label="摄影部" value="摄影部" />
                        <el-option label="视频推广部" value="视频推广部" />
                        <el-option label="闪客部" value="闪客部" />
                    </el-select>
                </el-form-item>
                <el-form-item label="计划">
                    <el-input v-model="formData.plan" placeholder="请输入计划" />
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="submitPlan">提交</el-button>
            </template>
        </el-dialog>

        <el-dialog v-model="viewDialogVisible" title="编辑">
            <el-form>
                <el-form-item label="ID" style="display: none;">
                    <el-input v-model="viewFormData.id" disabled />
                </el-form-item>
                <el-form-item label="日期">
                    <el-input v-model="viewFormData.date" disabled />
                </el-form-item>
                <el-form-item label="部门">
                    <el-select v-model="viewFormData.department">
                        <el-option label="综合部" value="综合部" />
                        <el-option label="程序部" value="程序部" />
                        <el-option label="美工部" value="美工部" />
                        <el-option label="媒体中心" value="媒体中心" />
                        <el-option label="共建中心" value="共建中心" />
                        <el-option label="摄影部" value="摄影部" />
                        <el-option label="视频推广部" value="视频推广部" />
                        <el-option label="闪客部" value="闪客部" />
                    </el-select>
                </el-form-item>
                <el-form-item label="计划">
                    <el-input v-model="viewFormData.plan" />
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="viewDialogVisible = false">取消</el-button>
                <el-button type="danger" @click="deletePlan">删除</el-button>
                <el-button type="primary" @click="updatePlan">确认</el-button>
            </template>
        </el-dialog>
    </div>
</template>

<style scoped>
.calendar-wrapper {
    padding: 16px;
}

.calendar-day {
    padding: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.date-text {
    font-size: 14px;
    font-weight: bold;
    margin-bottom: 4px;
    color: #333;
}

.event-badge {
    margin-top: 4px;
    text-align: center;
}

:deep .is-selected {
    color: #1989fa;
}

.example-showcase .el-loading-mask {
    z-index: 9;
}
</style>
