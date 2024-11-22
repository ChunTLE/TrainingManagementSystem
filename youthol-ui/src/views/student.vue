<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import http from '@/util/http'

const multipleTableRef = ref(null)
const multipleSelection = ref([])
const tableData = ref([])
const loading = ref(true)

const formInline = reactive({
    name: '',
    department: '',
    sdut_id: ''
})

const dialogEdit = ref(false)
const dialogInsert = ref(false)
const selectedEdit = reactive({
    sdut_id: '',
    name: '',
    department: '',
    identity: '',
    content: ''
})
const selectedInsert = reactive({
    sdut_id: '',
    name: '',
    department: '',
    identity: '',
    content: ''
})

const onInsert = () => {
    dialogInsert.value = true
}

const eddInsert = () => {
    http.post('/youtholer/create', selectedInsert).then(() => {
        dialogInsert.value = false
        getAllData()
        selectedInsert.sdut_id = ''
        selectedInsert.name = ''
        selectedInsert.department = ''
        selectedInsert.identity = ''
        selectedInsert.content = ''
    }).catch(err => {
        console.error(err)
    })
}

const onEdit = () => {
    if (multipleSelection.value.length === 1) {
        const item = multipleSelection.value[0]
        selectedEdit.sdut_id = item.sdut_id
        selectedEdit.name = item.name
        selectedEdit.department = item.department
        selectedEdit.identity = item.identity
        selectedEdit.content = item.content
        dialogEdit.value = true
    } else {
        alert("请先选择一条记录进行修改")
    }
}

const onUpdate = () => {
    http.put('/youtholer/update', selectedEdit).then(() => {
        dialogEdit.value = false
        getAllData()
    }).catch(err => {
        console.error(err)
    })
}

const onSubmit = () => {
    // 选择部门后又删除会导致 department 为 null
    if (formInline.name == null)
        formInline.name = ''
    if (formInline.department == null)
        formInline.department = ''
    http.post('/youtholer/search', formInline).then(res => {
        tableData.value = res.data.data
    }).catch(err => {
        console.error(err)
    })
}

const onDelete = () => {
    if (multipleSelection.value.length === 0) {
        ElMessage.warning('请先选择要删除的记录')
        return
    }
    ElMessageBox.confirm(
        `确定要删除选中的 ${multipleSelection.value.length} 条记录吗？`,
        '提示',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(() => {
        const ids = multipleSelection.value.map(item => item.sdut_id)
        http.post('/youtholer/delete', ids).then(() => {
            ElMessage.success('删除成功')
            getAllData()
            multipleSelection.value = []
        }).catch(err => {
            console.error(err)
            ElMessage.error('删除失败')
        })
    }).catch(() => {
        ElMessage.info('已取消删除')
    })
}

const handleSelectionChange = (val) => {
    multipleSelection.value = val
}

onMounted(() => {
    getAllData()
    loading.value = false
})

const getAllData = () => {
    http.get('/youtholer/all').then(res => {
        tableData.value = res.data.data
    })
}

</script>

<template>
    <!-- 查询 -->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="姓名">
            <el-input v-model="formInline.name" placeholder="请输入姓名" clearable />
        </el-form-item>
        <el-form-item label="部门">
            <el-select v-model="formInline.department" placeholder="请选择部门" clearable>
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
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
    </el-form>

    <!-- 操作 -->
    <div class="mb-4">
        <el-button type="primary" @click="onInsert">增加</el-button>
        <el-button type="success" @click="onEdit">修改</el-button>
        <el-button type="danger" @click="onDelete">删除</el-button>
    </div>

    <!-- 表格 -->
    <el-table v-loading="loading" element-loading-text="Loading..." ref="multipleTableRef" :data="tableData" stripe
        style="width: 100%" @selection-change="handleSelectionChange"
        :header-cell-style="{ background: 'rgb(248,248,249)' }">
        <el-table-column type="selection" width="55" />
        <el-table-column property="name" label="姓名" width="120" />
        <el-table-column property="department" label="部门" width="120" />
        <el-table-column property="identity" label="职位" width="120" />
        <el-table-column property="content" label="备注" />
    </el-table>

    <!-- 添加 -->
    <el-dialog v-model="dialogInsert" title="添加信息">
        <el-form :model="selectedInsert" label-width="80px">
            <el-form-item label="姓名">
                <el-input v-model="selectedInsert.name" placeholder="请输入姓名" />
            </el-form-item>
            <el-form-item label="学号">
                <el-input v-model="selectedInsert.sdut_id" placeholder="请输入学号" />
            </el-form-item>
            <el-form-item label="部门">
                <el-select v-model="selectedInsert.department" placeholder="请选择部门" clearable>
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
            <el-form-item label="职位">
                <el-select v-model="selectedInsert.identity" placeholder="请选择部门" clearable>
                    <el-option label="管理员" value="管理员" />
                    <el-option label="成员" value="成员" />
                </el-select>
            </el-form-item>
            <el-form-item label="备注">
                <el-input v-model="selectedInsert.content" placeholder="请输入内容" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer" style="text-align: right;">
                <el-button @click="dialogInsert = false">取消</el-button>
                <el-button type="primary" @click="eddInsert">确定</el-button>
            </div>
        </template>
    </el-dialog>

    <!-- 修改 -->
    <el-dialog v-model="dialogEdit" title="修改信息">
        <el-form :model="selectedEdit" label-width="80px">
            <el-form-item label="姓名">
                <el-input v-model="selectedEdit.name" placeholder="请输入姓名" />
            </el-form-item>
            <el-form-item label="部门">
                <el-select v-model="selectedEdit.department" placeholder="请选择部门" clearable>
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
            <el-form-item label="职位">
                <el-select v-model="selectedEdit.identity" placeholder="请选择部门" clearable>
                    <el-option label="管理员" value="管理员" />
                    <el-option label="成员" value="成员" />
                </el-select>
            </el-form-item>
            <el-form-item label="备注">
                <el-input v-model="selectedEdit.content" placeholder="请输入内容" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer" style="text-align: right;">
                <el-button @click="dialogEdit = false">取消</el-button>
                <el-button type="primary" @click="onUpdate">确定</el-button>
            </div>
        </template>
    </el-dialog>
</template>

<style scoped>
.demo-form-inline .el-input {
    --el-input-width: 220px;
}

.demo-form-inline .el-select {
    --el-select-width: 220px;
}

.example-showcase .el-loading-mask {
    z-index: 9;
}
</style>