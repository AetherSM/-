<script setup>
import { ref, onMounted } from 'vue'
import http from '../services/http'
const list = ref([])
const loading = ref(false)
const error = ref('')
const keyword = ref('')
const order = ref('desc')
const load = async () => {
  loading.value = true
  error.value = ''
  try {
    const { data } = await http.get('/products', { params: { keyword: keyword.value, sortBy: 'create_time', order: order.value } })
    if (data && data.code === 1) list.value = data.data || []
    else error.value = data?.msg || '加载失败'
  } catch (e) {
    error.value = '请求失败'
  } finally {
    loading.value = false
  }
}
onMounted(load)
</script>

<template>
  <div>
    <div class="toolbar">
      <input v-model="keyword" placeholder="搜索商品名称/描述" />
      <select v-model="order">
        <option value="desc">最新</option>
        <option value="asc">最早</option>
      </select>
      <button @click="load">搜索</button>
    </div>
    <div class="grid">
      <router-link v-for="p in list" :key="p.productId" :to="`/shop/${p.productId}`" class="card">
        <img :src="p.mainImage || 'https://via.placeholder.com/320x200?text=Product'" alt="" />
        <div class="name">{{ p.productName }}</div>
        <div class="price">
          <span class="current">¥{{ p.price }}</span>
          <span class="original" v-if="p.originalPrice">¥{{ p.originalPrice }}</span>
        </div>
      </router-link>
    </div>
    <div v-if="!loading && list.length===0" class="empty">暂无商品</div>
    <div v-if="error" class="error">{{ error }}</div>
  </div>
</template>

<style scoped>
.toolbar{display:flex;gap:8px;margin-bottom:12px}
.toolbar input,.toolbar select{padding:8px;border:1px solid #e5e7eb;border-radius:8px;background:#fff}
.grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(220px,1fr));gap:12px}
.card{display:block;border:1px solid #e5e7eb;border-radius:12px;overflow:hidden;background:#fff}
.card img{width:100%;height:160px;object-fit:cover;background:#f3f4f6}
.name{padding:8px 10px;font-weight:600}
.price{padding:0 10px 10px}
.current{color:#ef4444;font-weight:600;margin-right:8px}
.original{color:#9ca3af;text-decoration:line-through}
.empty{padding:24px;text-align:center;color:#999}
.error{padding:12px;color:#d33}
</style>
