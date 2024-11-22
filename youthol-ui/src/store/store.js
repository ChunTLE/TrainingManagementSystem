import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('userStore', () => {
    const sdut_id = ref('')
    const is_login = ref(false)
    const name = ref('')
    const department = ref('')
    const identity = ref()
    const token = ref('')
    const setToken = (newToken) => {
        token.value = newToken
    }
    const removeToken = () => {
        token.value = ''
    }
    return { sdut_id, is_login, name, department, identity, token, setToken, removeToken }
}, {
    persist: true
})

// export const useOAStore = defineStore('oaStore', () => {
//   // const oa_id = ref()
// })
