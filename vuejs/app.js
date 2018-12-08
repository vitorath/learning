// Events: https://www.w3schools.com/tags/ref_eventattributes.asp
// Events Modifiers: https://vuejs.org/v2/guide/events.html#Event-Modifiers
new Vue({
  el: '#vue-app',
  data: {
    age: 24,
    x: 0,
    y: 0
  },
  methods: {
    add: function(inc) {
      this.age += inc;
    },
    subtract: function(dec) {
      this.age -= dec;
    },
    updateXY: function(event) {
      console.log(event);
      this.x = event.offsetX;
      this.y = event.offsetY;
    },
    click: function() {
      alert('You clicked me');
    }
  }
});
