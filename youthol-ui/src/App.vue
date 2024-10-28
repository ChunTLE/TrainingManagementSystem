<script setup>
import { RouterView } from 'vue-router'
import { useUserStore } from './store/store';
import http from '@/util/http';

let store = useUserStore();


</script>

<template>
  <RouterView />
</template>

<style scoped>
</style>
