<script setup>
import { ref, onMounted } from 'vue'
import http from '../services/http'
const list = ref([])
const loading = ref(false)
const error = ref('')
const form = ref({ contactName: '', contactPhone: '', address: '', building: '', room: '' })
const load = async () => {
  loading.value = true
  error.value = ''
  try {
    const { data } = await http.get('/addresses')
    if (data && data.code === 1) list.value = data.data || []
    else error.value = data?.msg || '加载失败'
  } catch (e) {
    error.value = '请求失败'
  } finally {
    loading.value = false
  }
}
const add = async () => {
  try {
    const { data } = await http.post('/addresses', form.value)
    if (data && data.code === 1) {
      form.value = { contactName: '', contactPhone: '', address: '', building: '', room: '' }
      await load()
    } else error.value = data?.msg || '创建失败'
  } catch (e) { error.value = '请求失败' }
}
const removeItem = async (id) => {
  if (!confirm('确认删除该地址？')) return
  await http.delete(`/addresses/${id}`)
  await load()
}
const setDefault = async (id) => {
  await http.patch(`/addresses/${id}/default`)
  await load()
}
onMounted(load)
</script>

<template>
  <div>
    <div class="form">
      <h2>新增地址</h2>
      <div class="grid">
        <input v-model="form.contactName" placeholder="收货人" />
        <input v-model="form.contactPhone" placeholder="联系电话" />
        <input v-model="form.address" placeholder="详细地址" />
        <input v-model="form.building" placeholder="楼栋(可选)" />
        <input v-model="form.room" placeholder="房间号(可选)" />
      </div>
      <button class="btn" @click="add">保存</button>
    </div>
    <h2>我的地址</h2>
    <div class="list">
      <div v-for="a in list" :key="a.addressId" class="item">
        <div class="info">
          <div class="name">{{ a.contactName }}（{{ a.contactPhone }}）</div>
          <div class="addr">{{ a.address }} {{ a.building }} {{ a.room }}</div>
        </div>
        <div class="ops">
          <span v-if="a.isDefault===1" class="badge">默认</span>
          <button @click="setDefault(a.addressId)">设为默认</button>
          <button @click="removeItem(a.addressId)">删除</button>
        </div>
      </div>
    </div>
    <div v-if="error" class="error">{{ error }}</div>
  </div>
</template>

<style scoped>
.form{width:100%;padding:16px;border:1px solid #e5e7eb;border-radius:12px;background:#fff;margin-bottom:12px}
.grid{display:grid;grid-template-columns:repeat(3,1fr);gap:8px;margin-bottom:10px}
.grid input{padding:10px;border:1px solid #e5e7eb;border-radius:10px;background:#fff}
.btn{padding:10px 16px;border:none;border-radius:10px;background:#42b883;color:#fff;cursor:pointer}
.list{display:flex;flex-direction:column;gap:8px}
.item{display:flex;justify-content:space-between;align-items:center;padding:12px;border:1px solid #e5e7eb;border-radius:12px;background:#fff}
.name{font-weight:600}
.addr{color:#6b7280}
.ops{display:flex;gap:8px;align-items:center}
.badge{background:#42b883;color:#fff;border-radius:999px;padding:4px 8px;font-size:12px}
.error{padding:12px;color:#d33}
</style>
