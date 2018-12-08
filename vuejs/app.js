// Events: https://www.w3schools.com/tags/ref_eventattributes.asp
// Events Modifiers: https://vuejs.org/v2/guide/events.html#Event-Modifiers
new Vue({
  el: '#vue-app',
  data: {
    age: 20,
    a: 0,
    b: 0
  },
  methods: {
    /*addToA: function() {
      console.log('addToA');
      return this.age + this.a;
    },
    addToB: function() {
      console.log('addToB');
      return this.age + this.b;
    }*/
  },
  computed: {
    addToA: function() {
      console.log('addToA');
      return this.age + this.a;
    },
    addToB: function() {
      console.log('addToB');
      return this.age + this.b;
    }
  }
});
