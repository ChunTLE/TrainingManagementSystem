<script setup>
import http from '@/util/http';
http.get('/hello').then((res) => {
  console.log(res);
});
</script>

<template>
  <h1>Hello</h1>
</template>

<style scoped>

</style>