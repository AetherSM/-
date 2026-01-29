<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import http from '../services/http'
const router = useRouter()
const list = ref([])
const loading = ref(false)
const error = ref('')
const userType = Number(localStorage.getItem('userType') || 0)
const userId = Number(localStorage.getItem('userId') || 0)
const load = async () => {
  loading.value = true
  error.value = ''
  try {
    const { data } = await http.get('/api/errands/open')
    if (data && data.code === 1) {
      list.value = data.data || []
    } else {
      error.value = data && data.msg ? data.msg : '加载失败'
    }
  } catch (e) {
    error.value = '请求失败'
  } finally {
    loading.value = false
  }
}
onMounted(load)
const take = async (orderNo) => {
  try {
    const { data } = await http.post('/api/errands/take', null, { params: { orderNo, runnerId: userId } })
    if (data && data.code === 1) {
      await load()
      router.push('/errands/runner')
    }
    else error.value = data?.msg || '接单失败'
  } catch (e) { error.value = '请求失败' }
}
const goCreate = () => {
  router.push('/errands/create')
}
</script>

<template>
  <div>
    <div class="toolbar">
      <div class="title-bar">跑腿广场</div>
      <button v-if="userType===1" class="btn publish" @click="goCreate">发布跑腿</button>
    </div>
    <div class="grid">
    <div v-for="item in list" :key="item.orderId" class="card">
      <div class="row">
        <div class="title">{{ item.title }}</div>
        <div class="status">待接单</div>
      </div>
      <div class="desc">{{ item.description }}</div>
      <div class="row">
        <div>赏金 ¥{{ item.reward }}</div>
        <div>联系人 {{ item.contact_name || item.contactName }}</div>
      </div>
      <div class="row">
        <button v-if="userType===2" class="btn" @click="take(item.orderNo)">接单</button>
      </div>
    </div>
    <div v-if="!loading && list.length===0" class="empty">暂无数据</div>
    <div v-if="error" class="error">{{ error }}</div>
    </div>
  </div>
</template>

<style scoped>
.toolbar{display:flex;justify-content:space-between;align-items:center;margin-bottom:12px}
.title-bar{font-weight:700;color:#111827}
.grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(280px,1fr));gap:12px}
.card{padding:12px;border:1px solid #eee;border-radius:8px;background:#fff}
.row{display:flex;justify-content:space-between;align-items:center;margin:6px 0}
.title{font-weight:600}
.status{color:#42b883}
.desc{color:#666;font-size:14px}
.btn{padding:8px 12px;border:none;border-radius:10px;background:#42b883;color:#fff;cursor:pointer}
.btn.publish{background:#ff1f2d}
.empty{padding:24px;text-align:center;color:#999}
.error{padding:12px;color:#d33}
</style>
