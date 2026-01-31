CREATE TABLE IF NOT EXISTS invoices (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    pdf_url VARCHAR(255),
    user_id VARCHAR(255),
    amount INT
);