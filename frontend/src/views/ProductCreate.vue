<script setup>
import { ref } from 'vue'
import http from '../services/http'
const productName = ref('')
const description = ref('')
const categoryId = ref(1)
const price = ref(10)
const originalPrice = ref()
const stock = ref(10)
const mainImage = ref('')
const status = ref(1)
const message = ref('')
const loading = ref(false)
const submit = async () => {
  loading.value = true
  message.value = ''
  try {
    const body = {
      categoryId: categoryId.value,
      productName: productName.value,
      description: description.value,
      price: price.value,
      originalPrice: originalPrice.value,
      stock: stock.value,
      mainImage: mainImage.value,
      status: status.value
    }
    const { data } = await http.post('/products', body)
    if (data && data.code === 1) {
      message.value = '商品发布成功'
      productName.value = ''
      description.value = ''
      price.value = 10
      originalPrice.value = undefined
      stock.value = 10
      mainImage.value = ''
      status.value = 1
    } else {
      message.value = data?.msg || '发布失败'
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
    <h2>发布商品</h2>
    <div class="row"><label>名称</label><input v-model="productName" /></div>
    <div class="row"><label>描述</label><textarea v-model="description" /></div>
    <div class="row"><label>分类ID</label><input v-model.number="categoryId" type="number" min="1" /></div>
    <div class="row"><label>价格</label><input v-model.number="price" type="number" min="0" step="1" /></div>
    <div class="row"><label>原价</label><input v-model.number="originalPrice" type="number" min="0" step="1" /></div>
    <div class="row"><label>库存</label><input v-model.number="stock" type="number" min="0" step="1" /></div>
    <div class="row"><label>主图URL</label><input v-model="mainImage" /></div>
    <div class="row"><label>状态</label>
      <select v-model="status"><option :value="1">上架</option><option :value="0">下架</option></select>
    </div>
    <button class="btn" :disabled="loading" @click="submit">{{ loading ? '提交中' : '发布' }}</button>
    <div class="msg" v-if="message">{{ message }}</div>
    <div class="tip">注意：仅商家账号可发布商品</div>
  </div>
</template>

<style scoped>
.form{width:100%;padding:16px;border:1px solid #e5e7eb;border-radius:12px;background:#fff}
.row{display:flex;flex-direction:column;gap:6px;margin-bottom:12px}
.row input,.row textarea,.row select{padding:10px;border:1px solid #e5e7eb;border-radius:10px;background:#fff}
.btn{padding:10px 16px;border:none;border-radius:10px;background:#42b883;color:#fff;cursor:pointer}
.msg{margin-top:12px;color:#42b883}
.tip{margin-top:8px;color:#9ca3af}
</style>
