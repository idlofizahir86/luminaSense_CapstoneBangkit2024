const loginController = require('../controllers/controller-login');

module.exports = [
    {
        method: 'POST',
        path: '/login',
        handler: loginController.login
    },
    {
        method: 'GET',
        path: '/logout',
        handler: loginController.logout
    }
];
