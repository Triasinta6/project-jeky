function PageHeader({ kicker, title, description }) {
  return (
    <header className="topbar">
      <div>
        <p className="page-kicker">{kicker}</p>
        <h1>{title}</h1>
        <p>{description}</p>
      </div>
      <span className="admin-chip">Admin</span>
    </header>
  );
}

export default PageHeader;
