const profileController = {
    profile: async (request, h) => {
        const user = request.auth.credentials;
        
        if (!user) {
            return h.response({ message: 'Unauthorized' }).code(401);
        }

        return { message: 'User profile', user };
    }
};

module.exports = profileController;
