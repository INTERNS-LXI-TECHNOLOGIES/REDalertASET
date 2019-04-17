export const enum Relation {
  FATHER = 'FATHER',
  MOTHER = 'MOTHER',
  SISTER = 'SISTER',
  BROTHER = 'BROTHER',
  GUARDIAN = 'GUARDIAN',
  FRIEND = 'FRIEND',
  OTHER = 'OTHER'
}

export interface IContact {
  id?: number;
  firstName?: string;
  lastName?: string;
  phoneNumber?: number;
  relation?: Relation;
}

export const defaultValue: Readonly<IContact> = {};
