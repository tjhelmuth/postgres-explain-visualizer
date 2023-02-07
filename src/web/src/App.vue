<script setup>
import PlanView from "./components/PlanView.vue";
import {ref} from "vue";

//so we can rerender the plan component when the plan changes
let key = ref(0);
let planString = ref("");

let setPlan = (newPlan) => {
  planString.value = newPlan;
  key.value += 1;
}

window.setPlan = setPlan;
</script>

<template>
  <PlanView v-if="planString" :key="key" :plan-string="planString" />
</template>

<style scoped>

</style>