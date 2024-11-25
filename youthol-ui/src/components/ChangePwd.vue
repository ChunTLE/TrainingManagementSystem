<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/store'
import http from '@/util/http'

const userStore = useUserStore()

const router = useRouter()

const updateForm = ref({
    old_pwd: '',
    new_owd: '',
    re_pwd: ''
})

function handleLogin() {
    console.log(updateForm.value);
    http.patch('/auth/updatePwd', updateForm.value).then(res => {
        ElMessage.success('修改成功, 请重新登录');
        userStore.$patch({ sdut_id: '', is_login: false, token: '' })
        router.push('/login');
    }).catch(err => {
        console.log(err)
    })
}
</script>

<template>
    <div class="updateForm-container">
        <el-card class="box-card">
            <template #header>
                <div class="card-header">
                    <span>修改密码</span>
                </div>
            </template>
            <el-form :model="updateForm" label-width="80px">
                <el-form-item label="旧密码" prop="old_pwd">
                    <el-input v-model="updateForm.old_pwd" type="password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="new_owd">
                    <el-input v-model="updateForm.new_owd" type="password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="re_pwd">
                    <el-input v-model="updateForm.re_pwd" type="password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="handleLogin">提交</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<style scoped>
.updateForm-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

.box-card {
    width: 400px;
    padding: 20px;
}

.card-header {
    text-align: center;
    font-size: 18px;
    font-weight: bold;
}
</style>