<template>
  <form-helper>
    <div slot="form-header">
      <h3>This is the title of the form</h3>
      <p>Information about the form</p>
    </div>
    <div slot="form-fields">
      <input type="text" placeholder="name" required/>
      <input type="password" placeholder="password" required/>
    </div>
    <div slot="form-controls">
      <button type="button" v-on:click="handleSubmit">Submit</button>
    </div>
  </form-helper>
</template>

<script>

import FormHelper from './components/FormHelper.vue'

export default {
  components: {
    'form-helper': FormHelper
  },
  data () {
    return {
      title: 'I am a dynamic slot title'
    }
  },
  methods: {

  }
}
</script>

<style>

</style>
