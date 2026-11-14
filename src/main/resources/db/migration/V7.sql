-- =====================================================
-- V7: Add portfolio projects and update GitHub URLs
-- =====================================================

-- 1. Correct AI Fitness Tracker GitHub URL
UPDATE projects
SET project_link = 'https://github.com/Shiwans60/AI_Driven_FitnessTracker'
WHERE name = 'AI-Fitness-Tracker';


-- 2. Add AWS Shell Scripting
INSERT INTO projects (
    name, description, start_date, end_date, project_link
)
SELECT
    'aws-shell-scripting',
    'Demonstrates Linux shell scripting, AWS CLI usage, conditional statements, argument validation, and AWS resource management.',
    NULL,
    NULL,
    'https://github.com/Shiwans60/aws-shell-scripting'
    WHERE NOT EXISTS (
    SELECT 1 FROM projects
    WHERE LOWER(name) = LOWER('aws-shell-scripting')
);


-- 3. Add Checkout System
INSERT INTO projects (
    name, description, start_date, end_date, project_link
)
SELECT
    'checkout-system',
    'A production-style Spring Boot microservices architecture for an e-commerce checkout flow, featuring JWT authentication, API Gateway, secure inter-service communication, MongoDB Atlas integration, and modular Cart, Orders, Products, Inventory, and Auth services. Built with scalability principles and unit tests.',
    NULL,
    NULL,
    'https://github.com/Shiwans60/checkout-system'
    WHERE NOT EXISTS (
    SELECT 1 FROM projects
    WHERE LOWER(name) = LOWER('checkout-system')
);


-- 4. Add SetProds
INSERT INTO projects (
    name, description, start_date, end_date, project_link
)
SELECT
    'SetProds',
    'A secure and scalable inventory management application featuring REST APIs built with Java, Spring Boot, and JWT authentication, alongside a responsive React.js, TypeScript, and Vite frontend.',
    NULL,
    NULL,
    'https://github.com/Shiwans60/SetProds'
    WHERE NOT EXISTS (
    SELECT 1 FROM projects
    WHERE LOWER(name) = LOWER('SetProds')
);