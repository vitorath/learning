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
