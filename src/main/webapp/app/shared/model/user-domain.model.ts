import { IAlert } from 'app/shared/model/alert.model';
import { IContact } from 'app/shared/model/contact.model';
import { IRole } from 'app/shared/model/role.model';

export interface IUserDomain {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  password?: string;
  locality?: string;
  mobile?: number;
  activated?: boolean;
  alerts?: IAlert[];
  contacts?: IContact[];
  roles?: IRole[];
}

export const defaultValue: Readonly<IUserDomain> = {
  activated: false
};
