<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/store'
import http from '@/util/http'

const userStore = useUserStore()

const loginFormRef = ref(null)
const router = useRouter()

const loginForm = reactive({
    sdut_id: '',
    password: ''
})

function handleLogin() {
    loginFormRef.value.validate((valid) => {
        if (valid) {
            http.post('/auth/login', loginForm).then(res => {
                ElMessage.success('登录成功')
                userStore.$patch({
                    token: res.data
                })
                router.push('/')
            }).catch(err => {
                console.log(err)
            })
        } else {
            ElMessage.error('学号或密码错误')
            return false
        }
    })
}

function navigateToRegister() {
    router.push('/register')
}

function navigateToUpdate() {
    router.push('/updatePwd')
}
</script>

<template>
    <div class="login-container">
        <el-card class="box-card">
            <template #header>
                <div class="card-header">
                    <span>用户登录</span>
                </div>
            </template>
            <el-form :model="loginForm" ref="loginFormRef" label-width="80px">
                <el-form-item label="学号" prop="sdut_id">
                    <el-input v-model="loginForm.sdut_id" placeholder="请输入学号"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="handleLogin">登录</el-button>
                    <el-link type="primary" @click="navigateToRegister" style="float: right;margin-left: 50px;"> →
                        注册</el-link>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<style scoped>
.login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f0f2f5;
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