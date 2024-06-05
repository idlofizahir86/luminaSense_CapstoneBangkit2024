'use strict';

const Hapi = require('@hapi/hapi');
const loginRoutes = require('./src/routes/router-login');
const registerRoutes = require('./src/routes/router-register');
const profileRoutes = require('./src/routes/router-profile');

const init = async () => {

    const server = Hapi.server({
        port: 3000,
        host: 'localhost'
    });

    // Register routes
    server.route(loginRoutes);
    server.route(registerRoutes);
    server.route(profileRoutes);

    await server.start();
    console.log('Server running on %s', server.info.uri);
};

process.on('unhandledRejection', (err) => {
    console.log(err);
    process.exit(1);
});

init();
