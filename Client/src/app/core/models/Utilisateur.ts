export class Utilisateur {
  emailVerified: boolean;
  name: string;
  preferredUsername: string;
  givenName: string;
  familyName: string;
  email: string;

  constructor(data: Partial<Utilisateur> = {}) {
    this.emailVerified = data.emailVerified || false;
    this.name = data.name || '';
    this.preferredUsername = data.preferredUsername || '';
    this.givenName = data.givenName || '';
    this.familyName = data.familyName || '';
    this.email = data.email || '';
  }
}
