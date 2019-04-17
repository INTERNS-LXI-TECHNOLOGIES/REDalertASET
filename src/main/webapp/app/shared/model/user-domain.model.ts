import { IAlert } from 'app/shared/model/alert.model';
import { IContact } from 'app/shared/model/contact.model';

export interface IUserDomain {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  password?: string;
  locality?: string;
  mobile?: number;
  alerts?: IAlert[];
  contacts?: IContact[];
}

export const defaultValue: Readonly<IUserDomain> = {};
