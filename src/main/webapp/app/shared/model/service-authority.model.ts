export interface IServiceAuthority {
  id?: number;
  districtName?: string;
  authorityName?: string;
  phone?: number;
}

export const defaultValue: Readonly<IServiceAuthority> = {};
