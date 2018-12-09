<template>
  <ul>
    <li v-for="ninja in ninjas">{{ ninja }}</li>
  </ul>
</template>

<script>
export default {
  data () {
    return {
      ninjas: ['Vitor', 'Pikachu', 'Ivy']
    }
  }
}
</script>

<style>

</style>
