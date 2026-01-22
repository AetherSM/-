<script setup>
import { ref } from 'vue'
import http from '../services/http'
const title = ref('')
const description = ref('')
const pickupAddress = ref('')
const deliveryAddress = ref('')
const contactName = ref('')
const contactPhone = ref('')
const reward = ref(5)
const errandType = ref(1)
const message = ref('')
const loading = ref(false)
const submit = async () => {
  loading.value = true
  message.value = ''
  try {
    const body = {
      title: title.value,
      description: description.value,
      pickupAddress: pickupAddress.value,
      deliveryAddress: deliveryAddress.value,
      contactName: contactName.value,
      contactPhone: contactPhone.value,
      reward: reward.value,
      errandType: errandType.value
    }
    const { data } = await http.post('/api/errands/create', body)
    if (data && data.code === 1) {
      message.value = '订单创建成功'
      title.value = ''
      description.value = ''
      pickupAddress.value = ''
      deliveryAddress.value = ''
      contactName.value = ''
      contactPhone.value = ''
      reward.value = 5
      errandType.value = 1
    } else {
      message.value = data && data.msg ? data.msg : '创建失败'
    }
  } catch (e) {
    message.value = '请求失败'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="form">
    <h2>发布跑腿</h2>
    <div class="row">
      <label>标题</label>
      <input v-model="title" />
    </div>
    <div class="row">
      <label>描述</label>
      <textarea v-model="description" />
    </div>
    <div class="row">
      <label>取件地址</label>
      <input v-model="pickupAddress" />
    </div>
    <div class="row">
      <label>送达地址</label>
      <input v-model="deliveryAddress" />
    </div>
    <div class="row">
      <label>联系人</label>
      <input v-model="contactName" />
    </div>
    <div class="row">
      <label>联系电话</label>
      <input v-model="contactPhone" />
    </div>
    <div class="row">
      <label>类型</label>
      <select v-model="errandType">
        <option :value="1">取快递</option>
        <option :value="2">送外卖</option>
        <option :value="3">代买</option>
        <option :value="4">其他</option>
      </select>
    </div>
    <div class="row">
      <label>赏金</label>
      <input v-model.number="reward" type="number" min="0" step="1" />
    </div>
    <button class="btn" :disabled="loading" @click="submit">{{ loading ? '提交中' : '发布' }}</button>
    <div class="msg" v-if="message">{{ message }}</div>
  </div>
</template>

<style scoped>
.form{width:100%;padding:16px;border:1px solid #e5e7eb;border-radius:12px;background:#fff}
.row{display:flex;flex-direction:column;gap:6px;margin-bottom:12px}
.row input,.row textarea,.row select{padding:10px;border:1px solid #e5e7eb;border-radius:10px}
.btn{padding:10px 16px;border:none;border-radius:10px;background:#42b883;color:#fff;cursor:pointer}
.msg{margin-top:12px;color:#42b883}
</style>
