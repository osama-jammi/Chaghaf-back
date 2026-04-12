-- ================================================================
--  Chaghaf Backend · SQL Server Init Script
--  Creates all microservice databases
-- ================================================================

-- Auth DB
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chaghaf_auth')
    CREATE DATABASE chaghaf_auth COLLATE French_CI_AS;
GO

-- Subscription DB
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chaghaf_subscription')
    CREATE DATABASE chaghaf_subscription COLLATE French_CI_AS;
GO

-- Reservation DB
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chaghaf_reservation')
    CREATE DATABASE chaghaf_reservation COLLATE French_CI_AS;
GO

-- Boisson DB
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chaghaf_boisson')
    CREATE DATABASE chaghaf_boisson COLLATE French_CI_AS;
GO

-- Snack DB
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chaghaf_snack')
    CREATE DATABASE chaghaf_snack COLLATE French_CI_AS;
GO

-- Social DB
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chaghaf_social')
    CREATE DATABASE chaghaf_social COLLATE French_CI_AS;
GO

-- Notification DB
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chaghaf_notification')
    CREATE DATABASE chaghaf_notification COLLATE French_CI_AS;
GO

PRINT 'All Chaghaf databases created successfully.';
