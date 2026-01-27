<script setup>
import { ref, onMounted, watch } from 'vue'
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
const draftKey = 'errandDraft'
onMounted(() => {
  try {
    const raw = localStorage.getItem(draftKey)
    if (raw) {
      const d = JSON.parse(raw)
      if (d.title != null) title.value = d.title
      if (d.description != null) description.value = d.description
      if (d.pickupAddress != null) pickupAddress.value = d.pickupAddress
      if (d.deliveryAddress != null) deliveryAddress.value = d.deliveryAddress
      if (d.contactName != null) contactName.value = d.contactName
      if (d.contactPhone != null) contactPhone.value = d.contactPhone
      if (d.reward != null) reward.value = d.reward
      if (d.errandType != null) errandType.value = d.errandType
    }
  } catch (e) {}
})
const saveDraft = () => {
  const d = {
    title: title.value,
    description: description.value,
    pickupAddress: pickupAddress.value,
    deliveryAddress: deliveryAddress.value,
    contactName: contactName.value,
    contactPhone: contactPhone.value,
    reward: reward.value,
    errandType: errandType.value
  }
  try { localStorage.setItem(draftKey, JSON.stringify(d)) } catch (e) {}
}
watch([title, description, pickupAddress, deliveryAddress, contactName, contactPhone, reward, errandType], saveDraft)
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
      try { localStorage.removeItem(draftKey) } catch (e) {}
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
      <el-input v-model="title" placeholder="请输入标题" />
    </div>
    <div class="row">
      <label>描述</label>
      <el-input v-model="description" type="textarea" :rows="4" placeholder="请输入任务描述" />
    </div>
    <div class="row">
      <label>取件地址</label>
      <el-input v-model="pickupAddress" placeholder="请输入取件地址" />
    </div>
    <div class="row">
      <label>送达地址</label>
      <el-input v-model="deliveryAddress" placeholder="请输入送达地址" />
    </div>
    <div class="row">
      <label>联系人</label>
      <el-input v-model="contactName" placeholder="请输入联系人姓名" />
    </div>
    <div class="row">
      <label>联系电话</label>
      <el-input v-model="contactPhone" placeholder="请输入联系电话" />
    </div>
    <div class="row">
      <label>类型</label>
      <el-select v-model="errandType" placeholder="请选择类型">
        <el-option :value="1" label="取快递" />
        <el-option :value="2" label="送外卖" />
        <el-option :value="3" label="代买" />
        <el-option :value="4" label="其他" />
      </el-select>
    </div>
    <div class="row">
      <label>赏金</label>
      <el-input-number v-model="reward" :min="0" :step="1" />
    </div>
    <el-button type="primary" class="btn" :loading="loading" @click="submit">发布</el-button>
    <div class="msg" v-if="message">{{ message }}</div>
  </div>
</template>

<style scoped>
.form{width:100%;padding:16px;border:1px solid #e5e7eb;border-radius:12px;background:#fff}
.row{display:flex;flex-direction:column;gap:6px;margin-bottom:12px}
.btn{margin-top:6px}
.msg{margin-top:12px;color:#42b883}
</style>
