<script setup>
import { ref, onMounted } from 'vue'
import http from '../services/http'
import { ElMessage } from 'element-plus'
const list = ref([])
const loading = ref(false)
const error = ref('')
const keyword = ref('')
const order = ref('desc')
const addToCartLoading = ref(false)
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
const addToCart = async (id) => {
  if (addToCartLoading.value) return
  addToCartLoading.value = true
  try {
    const { data } = await http.post('/api/cart/add', null, { params: { productId: id, quantity: 1 } })
    if (data && data.code === 1) ElMessage.success('已加入购物车')
    else ElMessage.error(data?.msg || '加入购物车失败')
  } catch (e) {
    ElMessage.error('请求失败')
  } finally {
    addToCartLoading.value = false
  }
}
</script>

<template>
  <div>
    <div class="jd-header">
      <div class="logo">校园购</div>
      <div class="search">
        <input v-model="keyword" placeholder="搜索商品关键词" />
        <button class="search-btn" @click="load">搜索</button>
      </div>
      <div class="sort">
        <label>排序：</label>
        <select v-model="order" @change="load">
          <option value="desc">最新</option>
          <option value="asc">最早</option>
        </select>
      </div>
    </div>
    <div class="grid">
      <div v-for="p in list" :key="p.productId" class="card">
        <router-link :to="`/shop/${p.productId}`" class="thumb">
          <img :src="p.mainImage || 'https://via.placeholder.com/320x200?text=Product'" alt="" />
        </router-link>
        <div class="info">
          <div class="price">
            <span class="current">¥{{ p.price }}</span>
            <span class="original" v-if="p.originalPrice">¥{{ p.originalPrice }}</span>
          </div>
          <router-link :to="`/shop/${p.productId}`" class="name">{{ p.productName }}</router-link>
          <div class="tags">
            <span class="tag red">自营</span>
            <span class="tag green">满减</span>
          </div>
          <div class="actions">
            <button class="btn primary" @click="addToCart(p.productId)" :disabled="addToCartLoading">加入购物车</button>
            <router-link :to="`/shop/${p.productId}`" class="btn">查看详情</router-link>
          </div>
        </div>
      </div>
    </div>
    <div v-if="!loading && list.length===0" class="empty">暂无商品</div>
    <div v-if="error" class="error">{{ error }}</div>
  </div>
</template>

<style scoped>
.jd-header{display:flex;align-items:center;gap:12px;margin-bottom:16px;background:#fff;border:1px solid #e5e7eb;border-radius:12px;padding:12px}
.logo{font-weight:700;color:#111827}
.search{flex:1;display:flex;gap:8px}
.search input{flex:1;padding:10px;border:1px solid #e5e7eb;border-radius:10px}
.search-btn{padding:10px 16px;border:none;border-radius:10px;background:#42b883;color:#fff;cursor:pointer}
.sort select{padding:10px;border:1px solid #e5e7eb;border-radius:10px;background:#fff}
.grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(240px,1fr));gap:16px}
.card{border:1px solid #e5e7eb;border-radius:12px;background:#fff;overflow:hidden;transition:all .2s}
.card:hover{box-shadow:0 8px 24px rgba(0,0,0,.06);transform:translateY(-2px)}
.thumb img{width:100%;height:200px;object-fit:cover;background:#f3f4f6}
.info{padding:10px}
.price{margin:6px 0}
.current{color:#ef4444;font-weight:700;margin-right:8px;font-size:18px}
.original{color:#9ca3af;text-decoration:line-through}
.name{display:block;color:#111827;font-weight:600;line-height:1.4;margin:6px 0}
.tags{display:flex;gap:6px;margin:6px 0}
.tag{display:inline-block;padding:2px 6px;border-radius:6px;font-size:12px;border:1px solid #e5e7eb;color:#374151}
.tag.red{border-color:#ef4444;color:#ef4444}
.tag.green{border-color:#10b981;color:#10b981}
.actions{display:flex;gap:8px;margin-top:8px}
.btn{padding:8px 12px;border:none;border-radius:10px;background:#e5e7eb;color:#111827;cursor:pointer;text-align:center}
.btn.primary{background:#ff1f2d;color:#fff}
.empty{padding:24px;text-align:center;color:#999}
.error{padding:12px;color:#d33}
</style>
