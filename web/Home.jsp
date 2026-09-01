<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Smart City Platform</title>

<style>

*{
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:'Segoe UI',sans-serif;
}

html{
    scroll-behavior:smooth;
}

body{
    background:#f4f6f9;
}

/* NAVBAR */

.navbar{
    width:100%;
    background:white;
    display:flex;
    justify-content:space-between;
    align-items:center;
    padding:20px 80px;
    box-shadow:0 2px 10px rgba(0,0,0,0.1);
    position:fixed;
    top:0;
    z-index:1000;
}

.logo{
    font-size:32px;
    font-weight:bold;
    color:#0f172a;
}

.menu a{
    text-decoration:none;
    color:#333;
    margin-left:30px;
    font-size:17px;
    font-weight:600;
    transition:0.3s;
}

.menu a:hover{
    color:#2563eb;
}

/* HERO SECTION */

.hero{
    height:100vh;
    background:
    linear-gradient(
    rgba(0,0,0,0.65),
    rgba(0,0,0,0.65)),
    url('https://images.unsplash.com/photo-1477959858617-67f85cf4f1df');
    
    background-size:cover;
    background-position:center;

    display:flex;
    justify-content:center;
    align-items:center;
    text-align:center;

    color:white;
    padding:40px;
}

.hero-content{
    max-width:900px;
}

.hero h1{
    font-size:70px;
    margin-bottom:20px;
}

.hero h1 span{
    color:#38bdf8;
}

.hero p{
    font-size:22px;
    line-height:1.8;
    margin-bottom:40px;
}

/* BUTTONS */

.btn-area{
    display:flex;
    justify-content:center;
    gap:25px;
    flex-wrap:wrap;
}

.btn{
    padding:16px 40px;
    text-decoration:none;
    color:white;
    border-radius:10px;
    font-size:18px;
    font-weight:bold;
    transition:0.3s;
}

.btn:hover{
    transform:translateY(-4px);
}

.citizen{
    background:#06b6d4;
}

.admin{
    background:#ef4444;
}

/* ABOUT */

.section{
    padding:100px 80px;
    text-align:center;
}

.section h2{
    font-size:42px;
    color:#0f172a;
    margin-bottom:20px;
}

.section p{
    max-width:1000px;
    margin:auto;
    font-size:20px;
    line-height:1.8;
    color:#555;
}

/* SERVICES */

.services{
    background:white;
}

.service-grid{
    margin-top:50px;
    display:grid;
    grid-template-columns:repeat(auto-fit,minmax(280px,1fr));
    gap:25px;
}

.card{
    background:#f8fafc;
    padding:35px;
    border-radius:15px;
    box-shadow:0 4px 15px rgba(0,0,0,0.08);
}

.card h3{
    color:#2563eb;
    margin-bottom:15px;
}

.card p{
    font-size:16px;
}

/* CONTACT */

.contact{
    background:#0f172a;
    color:white;
}

.contact h2{
    color:white;
}

.contact p{
    color:#ddd;
}

/* FOOTER */

.footer{
    background:black;
    color:white;
    text-align:center;
    padding:20px;
}

</style>

</head>
<body>

<!-- NAVBAR -->

<div class="navbar">

    <div class="logo">
        SMART CITY
    </div>

    <div class="menu">
        <a href="#">Home</a>
        <a href="#about">About</a>
        <a href="#services">Services</a>
        <a href="#contact">Contact</a>
    </div>

</div>

<!-- HERO -->

<section class="hero">

    <div class="hero-content">

        <h1>
            Smart City <span>Digital Platform</span>
        </h1>

        <p>
            Connecting citizens, government departments,
            emergency services and public infrastructure
            through one integrated smart city ecosystem.
        </p>

        <div class="btn-area">

            <a href="login.html" class="btn citizen">
    Citizen Portal
</a>

            <a href="adminLogin.html" class="btn admin">
    Admin Portal
</a>

        </div>

    </div>

</section>

<!-- ABOUT -->

<section id="about" class="section">

    <h2>About Smart City</h2>

    <p>
        Smart City Platform provides a centralized
        governance system where citizens can submit
        complaints, request services, report emergencies,
        track infrastructure issues and interact directly
        with government departments in real time.
    </p>

</section>

<!-- SERVICES -->

<section id="services"
         class="section services">

    <h2>Our Services</h2>

    <div class="service-grid">

        <div class="card">
            <h3>Complaint Management</h3>
            <p>
                Submit and track public complaints online.
            </p>
        </div>

        <div class="card">
            <h3>Emergency Reporting</h3>
            <p>
                Report accidents, fires and emergencies.
            </p>
        </div>

        <div class="card">
            <h3>Service Requests</h3>
            <p>
                Request municipal and public services.
            </p>
        </div>

        <div class="card">
            <h3>Public Assets</h3>
            <p>
                Monitor city infrastructure and assets.
            </p>
        </div>

    </div>

</section>

<!-- CONTACT -->

<section id="contact"
         class="section contact">

    <h2>Contact Us</h2>

    <p>
        Email: smartcityportal11@gmail.com
        <br><br>
        Phone: +92-51-1234567
        <br><br>
        Address: Smart City Operations Center, Pakistan
    </p>

</section>

<!-- FOOTER -->

<div class="footer">
    © 2026 Smart City Digital Governance Platform
</div>

</body>
</html>