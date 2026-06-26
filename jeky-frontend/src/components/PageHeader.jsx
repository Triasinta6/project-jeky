import { Link } from "react-router-dom";

function PageHeader({ kicker, title, description, breadcrumb }) {
  return (
    <div className="page-header">
      <div>
        {kicker && <p className="page-kicker">{kicker}</p>}
        <h1>{title}</h1>
        {description && <p className="page-description">{description}</p>}
      </div>

      {breadcrumb && (
        <div className="page-breadcrumb">
          <Link to={breadcrumb.parentPath}>{breadcrumb.parentLabel}</Link>
          <span>/</span>
          <span>{breadcrumb.currentLabel}</span>
        </div>
      )}
    </div>
  );
}

export default PageHeader;