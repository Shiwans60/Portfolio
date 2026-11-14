-- Update Class XII coursework
UPDATE education
SET courses_taken = 'Computer Science, Mathematics, Physics'
WHERE university_name = 'Army Public School Fatehgarh'
  AND degree = 'Senior Secondary (Class XII)';

-- Associate school logo with both school qualifications
UPDATE education
SET image_path = '/assets/images/education/army-public-school-fatehgarh.png'
WHERE university_name = 'Army Public School Fatehgarh'
  AND degree IN (
                 'Senior Secondary (Class XII)',
                 'Secondary School Certificate (Class X)'
    );

-- 3. Add Army Institute of Technology logo
UPDATE education
SET image_path = '/assets/images/education/army-institute-of-technology.png'
WHERE university_name = 'Army Institute of Technology, Pune'
  AND degree = 'Bachelor of Engineering (Electronics and Telecommunication Engineering)';