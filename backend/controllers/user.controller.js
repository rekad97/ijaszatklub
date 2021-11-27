const express = require('express');
const router = express.Router();
const userService = require('../services/user.service');


router.post('/auth', auth);
router.post('/register', register);
router.get('/', getAll);
router.get('/current', getCurrent);
router.get('/:id', getById);
router.delete('/:id', _delete);

module.exports = router;

function auth(req, res, next) {
    console.log(req.body)
    userService.auth(req.body)
        .then(user => user ? res.json(user) : res.status(400).json({ message: 'Username or password is incorrect!' }))
        .catch(err => next(err));
}

function register(req, res, next) {
    userService.create(req.body)
        .then((user) => res.json(user))
        .catch(err => next(err));
}

function getAll(req, res, next) {
    userService.getAll()
        .then(users => res.json(users))
        .catch(err => next(err));
}

function getCurrent(req, res, next) {
    userService.getById(req.user.sub)
        .then(user => user ? res.json(user) : res.sendStatus(404))
        .catch(err => next(err));
}


function getById(req, res, next) {
    userService.getById(req.params.id)
        .then(user => {
            user ? res.json(user) : res.sendStatus(404);
            console.log("Usern in ID", user);
        })
        .catch(err => {
            console.log('getByid error', err);
            next(err)
        });
}



function _delete(req, res, next) {
    userService.delete(req.params.id)
        .then(() => res.json({}))
        .catch(err => next(err));
}


function verifyToken(req, res, next) {
    const token = req.headers.authorization.split(' ')[1];
    if (!token)
        return res.status(403).send({ auth: false, message: 'No token provided.' });
    console.log(token)
        // jwt.verify(token,'secret', function(err, decoded) {
        //   if (err)
        //   return res.status(500).send({ auth: false, message: err }); 
        //   //req.username = decoded.username;
        //   console.log(decoded)
        //   next();
        // });
    jwt.verify(token);
    next();
}