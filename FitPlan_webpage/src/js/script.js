// Mobile Navigation Toggle
document.addEventListener('DOMContentLoaded', function() {
    const hamburger = document.querySelector('.hamburger');
    const navMenu = document.querySelector('.nav-menu');
    
    // Toggle mobile menu
    hamburger.addEventListener('click', function() {
        hamburger.classList.toggle('active');
        navMenu.classList.toggle('active');
    });
    
    // Close mobile menu when clicking on a nav link
    document.querySelectorAll('.nav-menu a').forEach(link => {
        link.addEventListener('click', () => {
            hamburger.classList.remove('active');
            navMenu.classList.remove('active');
        });
    });
    
    // Smooth scrolling for navigation links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            const targetId = this.getAttribute('href');
            const targetElement = document.querySelector(targetId);
            
            if (targetElement) {
                const headerHeight = document.querySelector('header').offsetHeight;
                const targetPosition = targetElement.offsetTop - headerHeight;
                
                window.scrollTo({
                    top: targetPosition,
                    behavior: 'smooth'
                });
            }
        });
    });

    // Calorie Calculator Functionality
    const calorieForm = document.getElementById('calorie-form');
    const resultDiv = document.getElementById('result');
    
    if (calorieForm) {
        calorieForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Get form values
            const gender = document.getElementById('gender').value;
            const age = parseFloat(document.getElementById('age').value);
            const height = parseFloat(document.getElementById('height').value);
            const weight = parseFloat(document.getElementById('weight').value);
            const activityLevel = parseFloat(document.querySelector('input[name="activity"]:checked').value);
            
            // Calculate BMR (Basal Metabolic Rate) using Mifflin-St Jeor Equation
            let bmr;
            if (gender === 'male') {
                bmr = 10 * weight + 6.25 * height - 5 * age + 5;
            } else {
                bmr = 10 * weight + 6.25 * height - 5 * age - 161;
            }
            
            // Calculate TDEE (Total Daily Energy Expenditure)
            const tdee = bmr * activityLevel;
            
            // Display results
            document.getElementById('bmr-value').textContent = Math.round(bmr);
            document.getElementById('tdee-value').textContent = Math.round(tdee);
            
            // Calculate calorie goals
            document.getElementById('weight-loss').textContent = Math.round(tdee - (tdee * 0.2)); // 500 calorie deficit
            document.getElementById('maintenance').textContent = Math.round(tdee);
            document.getElementById('weight-gain').textContent = Math.round(tdee + (tdee * 0.15)); // 500 calorie surplus
            
            // Show results
            resultDiv.classList.remove('hidden');
            
            // Smooth scroll to results
            setTimeout(() => {
                resultDiv.scrollIntoView({ behavior: 'smooth', block: 'center' });
            }, 100);
        });
    }
    
    // Header scroll effect
    const header = document.querySelector('header');
    window.addEventListener('scroll', () => {
        if (window.scrollY > 50) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    });
    
    // Form validation
    const allInputs = document.querySelectorAll('input[type="number"]');
    allInputs.forEach(input => {
        input.addEventListener('input', () => {
            const value = parseFloat(input.value);
            const min = parseFloat(input.getAttribute('min'));
            const max = parseFloat(input.getAttribute('max'));
            
            if (value < min) {
                input.value = min;
            } else if (value > max) {
                input.value = max;
            }
        });
    });
});