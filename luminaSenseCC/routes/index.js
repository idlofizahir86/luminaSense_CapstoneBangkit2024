const { options } = require('@hapi/joi/lib/base');
const userController = require('../controllers/userController');

module.exports = [
    {
        method: 'POST',
        path: '/register',
        options : {
            auth: false
        },
        handler: userController.register
    },
    {
        method: 'POST',
        path: '/login',
        options : {
            auth: false
        },
        handler: userController.login
    },
    {
        method: 'GET',
        path: '/restricted',
        handler: (request, h) => {
            const user = request.auth.credentials.user;
            return { text: `Welcome, ${user.name}` };
        }
    }
];
