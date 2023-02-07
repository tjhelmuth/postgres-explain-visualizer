<script setup>
import {Plan} from "pev2";
import "pev2/dist/style.css"

defineProps({
  planString: {
    type: String,
    required: true
  }
});
</script>


<template>
  <div id="plan-container">
    <plan :plan-source="planString" plan-query=""></plan>
  </div>
</template>

<style scoped>
  #plan-container {
    width: 100vw;
    height: 100vh;
    display: flex;
  }
</style>