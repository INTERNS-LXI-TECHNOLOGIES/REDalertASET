import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from './user-management';
// prettier-ignore
import userDomain, {
  UserDomainState
} from 'app/entities/user-domain/user-domain.reducer';
// prettier-ignore
import contact, {
  ContactState
} from 'app/entities/contact/contact.reducer';
// prettier-ignore
import alert, {
  AlertState
} from 'app/entities/alert/alert.reducer';
// prettier-ignore
import location, {
  LocationState
} from 'app/entities/location/location.reducer';
// prettier-ignore
import emergencyService, {
  EmergencyServiceState
} from 'app/entities/emergency-service/emergency-service.reducer';
// prettier-ignore
import role, {
  RoleState
} from 'app/entities/role/role.reducer';

// prettier-ignore
import serviceAuthority, {
  ServiceAuthorityState
} from 'app/entities/service-authority/service-authority.reducer';

// prettier-ignore
import userDomain, {
  UserDomainState
} from 'app/entities/user-domain/user-domain.reducer';
// prettier-ignore
import contact, {
  ContactState
} from 'app/entities/contact/contact.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly userDomain: UserDomainState;
  readonly contact: ContactState;
  readonly alert: AlertState;
  readonly location: LocationState;
  readonly emergencyService: EmergencyServiceState;
  readonly role: RoleState;

  readonly serviceAuthority: ServiceAuthorityState;

  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  userDomain,
  contact,
  alert,
  location,
  emergencyService,
  role,
  serviceAuthority,

  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
