import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserDomain from './user-domain';
import Contact from './contact';
import Alert from './alert';
import Location from './location';
import EmergencyService from './emergency-service';
import Role from './role';
import ServiceAuthority from './service-authority';

/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/user-domain`} component={UserDomain} />
      <ErrorBoundaryRoute path={`${match.url}/contact`} component={Contact} />
      <ErrorBoundaryRoute path={`${match.url}/alert`} component={Alert} />
      <ErrorBoundaryRoute path={`${match.url}/location`} component={Location} />
      <ErrorBoundaryRoute path={`${match.url}/emergency-service`} component={EmergencyService} />
      <ErrorBoundaryRoute path={`${match.url}/role`} component={Role} />

      <ErrorBoundaryRoute path={`${match.url}/service-authority`} component={ServiceAuthority} />

      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
