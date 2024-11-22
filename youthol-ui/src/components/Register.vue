<script setup>
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router'
import http from '@/util/http';

const router = useRouter()

const registerForm = ref({
    sdut_id: '',
    password: '',
    name: '',
    confirmPassword: ''
});

// 定义验证函数
const validateConfirmPassword = (rule, value, callback) => {
    if (value !== registerForm.value.password) {
        callback(new Error('两次输入的密码不一致'));
    } else {
        callback();
    }
};

// 定义表单验证规则
const rules = reactive({
    name: [
        { required: true, message: '请输入姓名', trigger: 'blur' },
    ],
    sdut_id: [
        { required: true, message: '请输入学号', trigger: 'blur' },
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
    ],
    confirmPassword: [
        { required: true, message: '请再次输入密码', trigger: 'blur' },
        { validator: validateConfirmPassword, trigger: 'blur' }
    ]
});

const handleRegister = () => {
    console.log(registerForm.value);


    http.post('/auth/register', registerForm.value).then(res => {
        if (res.data.code === 0) {
            ElMessage.success('注册成功');
            router.push('/login');
        }
    }).catch(err => {
        console.log(err);
        ElMessage.error('注册失败，请检查输入信息');
        return false;
    })

};

const navigateToRegister = () => {
    router.push('/login');
};
</script>

<template>
    <div class="register-container">
        <el-card class="box-card">
            <template #header>
                <div class="card-header">
                    <span>用户注册</span>
                </div>
            </template>
            <el-form :model="registerForm" ref="form" label-width="80px" :rules="rules">
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="registerForm.name" placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item label="学号" prop="sdut_id">
                    <el-input v-model="registerForm.sdut_id" placeholder="请输入学号"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="registerForm.password" type="password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="confirmPassword">
                    <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请再次输入密码"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="handleRegister">注册</el-button>
                    <el-link type="primary" @click="navigateToRegister" style="float: right;margin-left: 50px;"> ←
                        返回</el-link>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<style scoped>
.register-container {
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