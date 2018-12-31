var userOne = {
  email: 'joaodasilva@test.com',
  name: 'João',
  login() {
    console.log(this.email, 'has logged in');
  },
  logout() {
    console.log(this.email, 'has logged out');
  }
};

console.log(userOne.name);
userOne.name = 'Mario';
console.log(userOne.name);
userOne['name'] = 'Vitor';
console.log(userOne.name);

var prop = 'name';
console.log(userOne[prop]);

userOne.age = 25;
userOne.logInfo = function() {};
console.log(userOne);
