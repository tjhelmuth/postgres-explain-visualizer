<script setup>
import PlanView from "./components/PlanView.vue";
import {ref} from "vue";

//so we can rerender the plan component when the plan changes
let key = ref(0);
let planString = ref("");

let loading = ref(false);
let setLoading = (newLoading) => {
  loading.value = newLoading;
}
window.setLoading = setLoading;

let setPlan = (newPlan) => {
  planString.value = newPlan;
  key.value += 1;
}

window.setPlan = setPlan;
</script>

<template>
  <div v-if="loading" id="overlay">
    <div id="overlay-text">
      Generating plan
    </div>
  </div>

  <PlanView v-if="planString" :key="key" :plan-string="planString" />
</template>

<style scoped>
  #overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(0,0,0,0.2);
    z-index: 100;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  #overlay-text {
    padding: 16px;
    background-color: rgb(0,0,0, 0.3);
    font-size: 2rem;
    border-radius: 4px;
    color: white;
  }
</style>