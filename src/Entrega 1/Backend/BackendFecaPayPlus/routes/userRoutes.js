const express = require('express')
const { userSignUp, getUsers, getUserByRa, deleteUser, userChangePassword } = require('../controllers/userController')
const { authLogin } = require('../controllers/authController')
const router = express.Router()

router.post('/cadastro', userSignUp)
router.post('/entrar', authLogin)

router.patch('/alterar-senha/:ra', userChangePassword)


router.get('/user/:ra', getUserByRa)
router.get('/users', getUsers)

router.delete('/deletar/:ra', deleteUser)

module.exports = router