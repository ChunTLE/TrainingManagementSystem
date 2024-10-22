import axios from 'axios';

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
const http = axios.create({
    baseURL: '/api',
    timeout: 10000
});

export default http;