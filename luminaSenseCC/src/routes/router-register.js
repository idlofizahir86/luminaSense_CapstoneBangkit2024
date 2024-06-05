const registerController = require('../controllers/controller-register');

module.exports = [
    {
        method: 'POST',
        path: '/register',
        handler: registerController.register
    }
];
