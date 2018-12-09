// Events: https://www.w3schools.com/tags/ref_eventattributes.asp
// Events Modifiers: https://vuejs.org/v2/guide/events.html#Event-Modifiers
new Vue({
  el: '#vue-app',
  data: {
    health: 100,
    ended: false
  },
  methods: {
    punch: function() {
      this.health -= 10;
      if (this.health <= 0) {
        this.ended = true;
      }
    },
    restart: function() {
      this.health = 100;
      this.ended = false;
    }
  },
  computed: {

  }
});
