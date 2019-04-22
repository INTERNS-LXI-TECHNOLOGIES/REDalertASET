import { IUserDomain } from 'app/shared/model/user-domain.model';

export interface IRole {
  id?: number;
  name?: string;
  users?: IUserDomain[];
}

export const defaultValue: Readonly<IRole> = {};
