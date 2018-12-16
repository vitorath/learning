<template>
  <div>
    <keep-alive>
      <component v-bind:is="component"></component>
    </keep-alive>
    <button type="button" v-on:click="component = 'form-one'">Show form one</button>
    <button type="button" v-on:click="component = 'form-two'">Show form two</button>
  </div>
</template>

<script>

import FormOne from './components/FormOne.vue'
import FormTwo from './components/FormTwo.vue'

export default {
  components: {
    'form-one': FormOne,
    'form-two': FormTwo
  },
  data () {
    return {
      component: 'form-one'
    }
  },
  methods: {

  }
}
</script>

<style>
body{
    margin: 0;
    font-family: 'Nunito SemiBold';
}
</style>
