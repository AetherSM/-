<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import http from '../services/http'
const route = useRoute()
const id = Number(route.params.id)
const product = ref(null)
const loading = ref(false)
const error = ref('')
const load = async () => {
  loading.value = true
  error.value = ''
  try {
    const { data } = await http.get(`/products/${id}`)
    if (data && data.code === 1) product.value = data.data
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
  <div v-if="product" class="detail">
    <img :src="product.mainImage || 'https://via.placeholder.com/600x360?text=Product'" alt="" />
    <div class="info">
      <h2>{{ product.productName }}</h2>
      <div class="price">
        <span class="current">¥{{ product.price }}</span>
        <span class="original" v-if="product.originalPrice">¥{{ product.originalPrice }}</span>
      </div>
      <p class="desc">{{ product.description }}</p>
      <div class="meta">库存 {{ product.stock }} ｜ 销量 {{ product.salesCount }}</div>
    </div>
  </div>
  <div v-else-if="!loading && !error" class="empty">暂无数据</div>
  <div v-if="error" class="error">{{ error }}</div>
</template>

<style scoped>
.detail{display:grid;grid-template-columns:1fr 1fr;gap:16px;background:#fff;border:1px solid #e5e7eb;border-radius:12px;padding:16px}
.detail img{width:100%;height:360px;object-fit:cover;background:#f3f4f6;border-radius:12px}
.price{margin:6px 0}
.current{color:#ef4444;font-weight:700;margin-right:8px}
.original{color:#9ca3af;text-decoration:line-through}
.desc{color:#374151}
.meta{color:#6b7280;margin-top:8px}
.empty{padding:24px;text-align:center;color:#999}
.error{padding:12px;color:#d33}
</style>
