import ShowBlogs from './components/ShowBlogs.vue'
import AddBlog from './components/AddBlog.vue'
import singleBlog from './components/singleBlog.vue'

export default [
  {path: '/', component: ShowBlogs},
  {path: '/add', component: AddBlog},
  {path: '/blog/:id', component: singleBlog}
]
