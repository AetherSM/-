<script setup>
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import MyOrders from './MyOrders.vue'
import Wallet from './Wallet.vue'
const route = useRoute()
const router = useRouter()
const activeTab = ref('orders')
const syncFromQuery = () => {
  const t = route.query.tab
  activeTab.value = (t === 'wallet') ? 'wallet' : 'orders'
}
onMounted(syncFromQuery)
watch(() => route.query.tab, syncFromQuery)
const switchTab = (name) => {
  activeTab.value = name
  router.replace({ path: '/my', query: { tab: name } })
}
</script>

<template>
  <div class="my">
    <div class="tabs">
      <button class="tab" :class="{active: activeTab==='orders'}" @click="switchTab('orders')">订单</button>
      <button class="tab" :class="{active: activeTab==='wallet'}" @click="switchTab('wallet')">钱包充值</button>
    </div>
    <div class="panel">
      <MyOrders v-if="activeTab==='orders'" />
      <Wallet v-else />
    </div>
  </div>
</template>

<style scoped>
.my{display:flex;flex-direction:column;gap:12px}
.tabs{display:flex;gap:8px;background:#fff;border:1px solid #e5e7eb;border-radius:12px;padding:8px}
.tab{flex:1;padding:10px;border:none;border-radius:8px;background:#f3f4f6;color:#111827;cursor:pointer}
.tab.active{background:#ff1f2d;color:#fff;font-weight:600}
.panel{background:#fff;border:1px solid #e5e7eb;border-radius:12px;padding:12px}
</style>
