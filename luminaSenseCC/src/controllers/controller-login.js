const users = []; // This should be replaced with actual database logic

const loginController = {
    login: async (request, h) => {
        const { email, password } = request.payload || {}; // Add default empty object
        if (!email || !password) {
            return h.response({ message: 'Email and password are required' }).code(400);
        }
        const user = users.find(u => u.email === email && u.password === password);
        
        if (!user) {
            return h.response({ message: 'Invalid email or password' }).code(401);
        }

        request.cookieAuth.set({ id: user.id });
        return { message: 'Login successful' };
    },
    logout: (request, h) => {
        request.cookieAuth.clear();
        return { message: 'Logout successful' };
    }
};

module.exports = loginController;
