<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import http from '../services/http'
import { ElMessage } from 'element-plus'
const route = useRoute()
const id = Number(route.params.id)
const product = ref(null)
const loading = ref(false)
const error = ref('')
const qty = ref(1)
const addresses = ref([])
const chooseVisible = ref(false)
const addressId = ref(null)
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
const addToCart = async () => {
  try {
    const { data } = await http.post('/api/cart/add', null, { params: { productId: id, quantity: qty.value } })
    if (data && data.code === 1) ElMessage.success('已加入购物车')
    else ElMessage.error(data?.msg || '加入购物车失败')
  } catch (e) {
    ElMessage.error('请求失败')
  }
}
const openBuy = async () => {
  try {
    const { data } = await http.get('/addresses')
    if (data && data.code === 1) {
      addresses.value = data.data || []
      addressId.value = addresses.value[0]?.addressId || null
      chooseVisible.value = true
    }
  } catch (e) {}
}
const payNow = async () => {
  if (!addressId.value) { ElMessage.error('请选择地址'); return }
  try {
    const dto = {
      addressId: addressId.value,
      items: [{ productId: id, quantity: qty.value }],
      remark: ''
    }
    const { data } = await http.post('/orders', dto)
    if (data && data.code === 1 && data.data && data.data.orderNo) {
      const orderNo = data.data.orderNo
      await http.post(`/orders/${orderNo}/pay`)
      ElMessage.success('支付成功')
    } else {
      ElMessage.error(data?.msg || '下单失败')
    }
  } catch (e) {
    ElMessage.error('请求失败')
  } finally {
    chooseVisible.value = false
  }
}
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
      <div class="actions">
        <input class="qty" v-model.number="qty" type="number" min="1" />
        <button class="btn" @click="addToCart">加入购物车</button>
        <button class="btn primary" @click="openBuy">立即购买</button>
      </div>
    </div>
  </div>
  <div v-else-if="!loading && !error" class="empty">暂无数据</div>
  <div v-if="error" class="error">{{ error }}</div>
  <el-dialog v-model="chooseVisible" title="选择收货地址" width="520px">
    <div class="addr-list">
      <label v-for="a in addresses" :key="a.addressId" class="addr-item">
        <input type="radio" :value="a.addressId" v-model="addressId" />
        <span>{{ a.contactName }}（{{ a.contactPhone }}）{{ a.address }} {{ a.building }} {{ a.room }}</span>
      </label>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <button class="btn" @click="payNow">支付</button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>
.detail{display:grid;grid-template-columns:1fr 1fr;gap:16px;background:#fff;border:1px solid #e5e7eb;border-radius:12px;padding:16px}
.detail img{width:100%;height:360px;object-fit:cover;background:#f3f4f6;border-radius:12px}
.price{margin:6px 0}
.current{color:#ef4444;font-weight:700;margin-right:8px}
.original{color:#9ca3af;text-decoration:line-through}
.desc{color:#374151}
.meta{color:#6b7280;margin-top:8px}
.actions{display:flex;gap:8px;margin-top:12px}
.qty{width:80px;padding:8px;border:1px solid #e5e7eb;border-radius:10px}
.btn{padding:10px 16px;border:none;border-radius:10px;background:#e5e7eb;color:#111827;cursor:pointer}
.btn.primary{background:#42b883;color:#fff}
.empty{padding:24px;text-align:center;color:#999}
.error{padding:12px;color:#d33}
.addr-list{display:flex;flex-direction:column;gap:8px}
.addr-item{display:flex;gap:8px;align-items:center}
</style>
