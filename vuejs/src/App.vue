<!-- Reference Type: the variable is insert using props. If in any components it's edit, its change the original -->
<template>
  <div>
    <app-header v-bind:title="title" v-on:changeTitle="updateTitle($event)"></app-header>
    <app-ninjas v-bind:ninjas="ninjas"></app-ninjas>
    <app-footer v-bind:title="title"></app-footer>
  </div>
</template>

<script>
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'
import Ninjas from './components/Ninjas.vue'

export default {
  components: {
    'app-header': Header,
    'app-footer': Footer,
    'app-ninjas': Ninjas
  },
  data () {
    return {
      ninjas: [
        {name: 'Vitor', speciality: 'AI Programmer', show: false},
        {name: 'Ryu', speciality: 'Vue Components', show: false},
        {name: 'Hitoshi', speciality: 'Click Events', show: false},
        {name: 'Crystal', speciality: 'HTML Wizardry', show: false},
        {name: 'Tango', speciality: 'Conditionals', show: false},
        {name: 'Kami', speciality: 'Webpack', show: false}
      ],
      title: 'Vue Ninjas'
    }
  },
  methods: {
    updateTitle: function(title) {
      this.title = title;
    }
  }
}
</script>

<style>

</style>
